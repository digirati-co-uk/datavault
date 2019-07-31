<#import "*/layout/defaultlayout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="admin">
<@layout.vaultLayout>

    <style>
        #add-new {
            margin: 2em 0;
        }
        #role-assignments {
            padding: 0 2em 0 0;
        }
        .action-column {
            width: 100px;
            text-align: center;
        }
        .role-definitions {
            border-left: 1px solid;
            padding: 0 1em;
        }
        .role-definitions .role-definition-title {
            margin-top: 0;
        }
        .role-definitions .definition {
            margin: 0.5em 0;
        }
        .btn-delete {
            color: #b94a48;
            background-color: #f2dede;
        }
    </style>

    <div id="add-new-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="add-new-user-title" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="create-form" class="form form-horizontal" role="form" action="${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button>
                        <h4 class="modal-title" id="add-new-user-title">Add new user</h4>
                    </div>
                    <div class="modal-body">
                        <div id="create-error" class="alert alert-danger hidden" role="alert"></div>
                        <div class="form-group ui-widget">
                            <label for="new-user-name" class="control-label col-sm-2">Name:</label>
                            <div class="col-sm-10">
                                <input id="new-user-name" type="text" class="form-control" name="user" value=""/>
                            </div>
                        </div>
                        <div class="form-group ui-widget">
                            <label for="new-user-role" class="control-label col-sm-2">Role:</label>
                            <div class="col-sm-10">
                                <select id="new-user-role" name="role" class="form-control">
                                    <#list roles as role><option value="${role.id}">${role.name}</option></#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="submitAction" name="action" value="submit"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary btn-ok">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div id="update-existing-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="update-existing-title" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="update-form" class="form form-horizontal" role="form" action="${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user/update" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button>
                        <h4 class="modal-title" id="update-existing-title">Update user</h4>
                    </div>
                    <div class="modal-body">
                        <div id="update-error" class="alert alert-danger hidden" role="alert"></div>
                        <div class="form-group ui-widget">
                            <label for="role-update-user-name" class="control-label col-sm-2">Name:</label>
                            <div class="col-sm-10">
                                <input id="role-update-user-name" type="text" class="form-control" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group ui-widget">
                            <label for="update-user-role" class="control-label col-sm-2">Role:</label>
                            <div class="col-sm-10">
                                <select id="update-user-role" name="role" class="form-control">
                                    <#list roles as role><option value="${role.id}">${role.name}</option></#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="role-update-user-id" name="user"/>
                    <input type="hidden" id="submitAction" name="action" value="submit"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary btn-ok">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div id="delete-dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="delete-title" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="delete-form" class="form form-horizontal" role="form" action="${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user/delete" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times" aria-hidden="true"></i></button>
                        <h4 class="modal-title" id="delete-title">Remove user</h4>
                    </div>
                    <div id="delete-error" class="alert alert-danger hidden" role="alert"></div>
                    <div class="modal-body">
                        <label>Are you sure you want to remove the role assignment for user <span id="delete-role-user-name"></span>?</label>
                    </div>
                    <input type="hidden" id="delete-role-user-id" name="user"/>
                    <input type="hidden" id="submitAction" name="action" value="submit"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                        <button type="submit" class="btn btn-primary btn-ok">Yes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="container">

        <ol class="breadcrumb">
            <li><a href="${springMacroRequestContext.getContextPath()}/admin"><b>Administration</b></a></li>
            <li><a href="${springMacroRequestContext.getContextPath()}/admin/schools"><b>Schools</b></a></li>
            <li class="active"><b>${school.name}</b></li>
        </ol>

        <h1 id="role-assignments-title">${school.name}</h1>

        <div id="add-new">
            <a href="#" data-toggle="modal" data-target="#add-new-dialog">+ Add new user to school</a>
        </div>

        <div id="role-assignments" class="table-responsive col-md-8">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Role</th><!-- TODO need to add ordering on this -->
                        <th class="action-column">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <#list roleAssignments as assignment>
                        <tr>
                            <td>${assignment.user.firstname} ${assignment.user.lastname}</td>
                            <td>${assignment.role.name}</td>
                            <td class="action-column">
                                <a href="#" class="btn btn-default" data-toggle="modal" data-target="#update-existing-dialog" data-user-id="${assignment.user.getID()}" data-user-name="${assignment.user.firstname} ${assignment.user.lastname}" title="Edit role assignment for user ${assignment.user.firstname} ${assignment.user.lastname}."><i class="fa fa-pencil"></i></a>
                                <a href="#" class="btn btn-default btn-delete" data-toggle="modal" data-target="#delete-dialog" data-user-id="${assignment.user.getID()}" data-user-name="${assignment.user.firstname} ${assignment.user.lastname}" title="Remove role assignment for user ${assignment.user.firstname} ${assignment.user.lastname}."><i class="fa fa-trash"></i></a>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>

        <div id="role-definitions" class="col-md-4">
            <div class="role-definitions">
                <h3 class="role-definition-title">Role Definitions</h3>
                <#list roles as role>
                    <div class="definition">
                        <b>${role.name}:</b> ${role.description}
                    </div>
                </#list>
            </div>
        </div>

    </div>

    <script>
        $('[data-target="#add-new-dialog"]').click(function() {
            $('#create-error').addClass('hidden').text('');
        });
        $('[data-target="#update-existing-dialog"]').click(function() {
            var userId = $(this).data('user-id');
            var userName = $(this).data('user-name');
            $('#role-update-user-id').val(userId);
            $('#role-update-user-name').val(userName);
            $('#update-error').addClass('hidden').text('');
        });
        $('[data-target="#delete-dialog"]').click(function() {
            var userId = $(this).data('user-id');
            var userName = $(this).data('user-name');
            $('#delete-role-user-id').val(userId);
            $('#delete-role-user-name').text(userName);
            $('#delete-error').addClass('hidden').text('');
        });

        $('#create-form').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                method: 'POST',
                url: '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user',
                data: formData,
                success: function() {
                    window.location.href = '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}';
                },
                error: function(xhr) {
                    var $error = $('#create-error').removeClass('hidden');
                    if (xhr.status === 422) {
                        $error.text(xhr.responseText);
                    } else {
                        $error.text('An error occurred. Please contact your system administrator.');
                    }
                }
            });
        });

        $('#update-form').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                method: 'POST',
                url: '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user/update',
                data: formData,
                success: function() {
                    window.location.href = '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}';
                },
                error: function(xhr) {
                    var $error = $('#update-error').removeClass('hidden');
                    if (xhr.status === 422) {
                        $error.text(xhr.responseText);
                    } else {
                        $error.text('An error occurred. Please contact your system administrator.');
                    }
                }
            });
        });

        $('#delete-form').submit(function(event) {
            event.preventDefault();
            var formData = $(this).serialize();
            $.ajax({
                method: 'POST',
                url: '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}/user/delete',
                data: formData,
                success: function() {
                    window.location.href = '${springMacroRequestContext.getContextPath()}/admin/schools/${school.getID()}';
                },
                error: function(xhr) {
                    var $error = $('#delete-error').removeClass('hidden');
                    if (xhr.status === 422) {
                        $error.text(xhr.responseText);
                    } else {
                        $error.text('An error occurred. Please contact your system administrator.');
                    }
                }
            });
        });

        $("#new-user-name").autocomplete({
            autoFocus: true,
            appendTo: "#add-new-dialog",
            minLength: 2,
            source: function(request, response) {
                var term = request.term;
                $.ajax({
                    url: "${springMacroRequestContext.getContextPath()}/vaults/autocompleteuun/"+term,
                    type: 'GET',
                    dataType: "json",
                    success: function( data ) {
                        response( data );
                    }
                });
            },
            select: function(event, ui) {
                var attributes = ui.item.value.split(" - ");
                this.value = attributes[0];
                return false;
            }
        });
    </script>

</@layout.vaultLayout>