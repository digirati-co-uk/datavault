#!/usr/bin/perl
use strict;
use Mail::IMAPClient;
use IO::Socket;
use IO::Socket::SSL;
use Time::ParseDate;
use Data::Dumper;
use MIME::Parser;

# Config stuff
my $mail_hostname = 'outlook.office365.com';
my $mail_username = 'student@ed.ac.uk';
my $mail_password = '123456';
my $mail_ssl = 1;
my $message_file = '/tmp/message.out';
my $attachment_file_prefix = '/tmp/attachment.';
my $xls_mime_type = 'application/vnd.ms-excel';

# Make sure this is accessable for this namespace
my $socket = undef;

if( $mail_ssl ) {
	# Open up a SSL socket to use with IMAPClient later
	$socket = IO::Socket::SSL->new(
		PeerAddr => $mail_hostname,
		PeerPort => 993,
		Timeout => 5,
	);
} else {
	# Open up a none SSL socket to use with IMAPClient later
	$socket = IO::Socket::INET->new(
		PeerAddr => $mail_hostname,
		PeerPort => 143,
		Timeout => 5,
	);
}

# Check we connected
if( ! defined( $socket ) ) {
	print STDERR "Could not open socket to mailserver: $@\n";
	exit 1;
}

my $client = Mail::IMAPClient->new(
	Socket   => $socket,
	User     => $mail_username,
	Password => $mail_password,
	Timeout => 5,
);

# Check we have an imap client
if( ! defined( $client ) ) {
	print STDERR "Could not initialize the imap client: $@\n";
	exit 1;
}

# Check we are authenticated
if( $client->IsAuthenticated() ) {
	# Select the INBOX folder
	if( ! $client->select("INBOX") ) {
		print STDERR "Could not select the INBOX: $@\n";
	} else {
		if( $client->message_count("INBOX") > 0) {
			print "Processing " . $client->message_count("INBOX") . " messages....\n";

			# We delete messages after processing so get all in the inbox
			#my @unread = $client->unseen or warn "Could not find unseen msgs: $@\n";
		    #foreach my $mail (@unread){
			foreach my $seqNo ($client->search('ALL') ) {

				# Pull the RFC822 date out the message
				#my $date = $client->date( $seqNo );

				# Pull the subject out the message
				my $subject = $client->subject( $seqNo );

			#	# Pull the body out the message
				#my $body = $client->body_string( $seqNo );

				# Try and get the unix time of the message being sent
				#my $unix_date = undef;
				#if( $date ) {
				#	$unix_date = parsedate( $date );
				#}

				# Log the recieved time
				#my $recv_unix = time;

				# Check we have valid stuff
				#if( ! $unix_date || ! $subject || ! $body ) {
				#	print Dumper( $unix_date );
			#		print Dumper( $subject );
				#	print Dumper( $body );
				#} else {
				if ($subject eq 'DV PURE FLAT FILE') {
					my $parser = MIME::Parser->new( );
					$parser->output_dir("/tmp");
					
					open my $emailFileHandle, ">", $message_file;
					$client->message_to_file($emailFileHandle, $seqNo);
					close $emailFileHandle;	
					
					my $entity = $parser->parse_open($message_file);
					$entity->dump_skeleton;

					my $head     = $entity->head( );                 # object--see docs
					my $preamble = $entity->preamble;               # ref to array of lines
					my $epilogue = $entity->epilogue;               # ref to array of lines
					
					print "head => '$head'\n";
					print "preamble => '$preamble'\n";
					print "epilogue => '$epilogue'\n";

					my $num_parts = $entity->parts;
					print "num_parts = '$num_parts'\n";
					for (my $i=0; $i < $num_parts; $i++) {
					  my $part         = $entity->parts($i);
					  my $content_type = $part->mime_type;
					  my $body         = $part->as_string;
					  my $bh   = $part->bodyhandle;
					  print "MIME Type: $content_type\n";
					  if ($content_type eq $xls_mime_type) {
						my $content .= $bh->as_string if defined $bh;
						my $attachmentFile = $attachment_file_prefix . $i;
						open( my $OUTFILE, ">", $attachmentFile );
						print $OUTFILE "$content";
						close( $OUTFILE );
					  }
					}
					# remember to clean tmp files
					$parser->filer->purge;
					unlink $message_file;
					#	# Remove the message
					#	$client->delete_message($seqNo);
				}
				#}
			}

			# Delete the messages we have deleted
			# Yes, you read that right, IMAP is strangly awesome
			#$client->expunge("INBOX");
		} else {
			# No messages
			print "No messages to process\n";
		}

		# Close the inbox
		$client->close("INBOX");
	}
} else {
	print STDERR "Could not authenticate against IMAP: $@\n";
	exit 1;
}

# Tidy up after we are done
$client->done();
exit 0;
