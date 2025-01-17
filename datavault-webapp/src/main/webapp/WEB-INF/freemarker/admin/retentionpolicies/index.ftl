<#import "*/layout/defaultlayout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="admin">
<@layout.vaultLayout>

<div class="container">

    <ol class="breadcrumb">
        <li><a href="${springMacroRequestContext.getContextPath()}/admin/"><b>Administration</b></a></li>
        <li class="active"><b>Retention Policies</b></li>
    </ol>

    <#if policies?has_content>
        <div class="table-responsive">
            <table class="table table-striped">

                <thead>
                    <tr class="tr">
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Engine</th>
                        <th>Sort order</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    <#list policies as policy>
                        <tr class="tr">
                            <td>${policy.getID()?html}</td>
                            <td>
                                <input id="${policy.getID()}-name" value="${policy.name?html}"/>
                            </td>
                            <td>
                                <textarea id="${policy.getID()}-description" class="form-control" name="${policy.getID()}-description" rows="3">${policy.description!?html}</textarea>
                            </td>
                            <td><span class="label label-default">${policy.engine?html}</span></td>
                            <td>${policy.sort?html}</td>
                            <td><a class="update-btn btn btn-default btn-sm" href="#" data-policyid="${policy.getID()}">Update Policy</a></td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </#if>

</div>
<script>
    // Add Spring Security CSRF header to ajax requests
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    // Handle description update
    $('.update-btn').click(function() {
        var policy_id = $(this).data('policyid');

        var new_name = $("#"+policy_id+"-name").val();
        var new_description = $("#"+policy_id+"-description").val();

        $.ajax({
            url: '${springMacroRequestContext.getContextPath()}/admin/retentionpolicies/'+policy_id+'/update',
            type: 'POST',
            data: {
                name : new_name,
                description : new_description
            },
            error: function(xhr, ajaxOptions, thrownError) {
                alert('Error: unable to update policy name');
            }
        });
    });
</script>
</@layout.vaultLayout>
