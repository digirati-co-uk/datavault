#!/usr/bin/perl
use strict;
use Mail::IMAPClient;
use IO::Socket;
use IO::Socket::SSL;
use Time::ParseDate;
use Data::Dumper;

# Config stuff
my $mail_hostname = 'outlook.office365.com';
my $mail_username = 'dspeed2@ed.ac.uk';
my $mail_password = '12345';
my $mail_ssl = 1;

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
			#for( $client->search('ALL') ) {
			#	print "   ..." . $_ . "\n";

			#	# Pull the RFC822 date out the message
			#	my $date = $client->date( $_ );

			#	# Pull the subject out the message
			#	my $subject = $client->subject( $_ );

			#	# Pull the body out the message
			#	my $body = $client->body_string( $_ );

			#	# Try and get the unix time of the message being sent
			#	my $unix_date = undef;
			#	if( $date ) {
			#		$unix_date = parsedate( $date );
			#	}

			#	# Log the recieved time
			#	my $recv_unix = time;

			#	# Check we have valid stuff
			#	if( ! $unix_date || ! $subject || ! $body ) {
			#		print Dumper( $unix_date );
			#		print Dumper( $subject );
			#		print Dumper( $body );
			#	} else {

			#	}

			#	# Remove the message
			#	$client->delete_message($_);
			#}

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
