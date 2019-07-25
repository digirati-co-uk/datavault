<#import "*/layout/defaultlayout.ftl" as layout>
<#-- Specify which navbar element should be flagged as active -->
<#global nav="admin">
<@layout.vaultLayout>

    <div class="container">

        <ol class="breadcrumb">
            <li><a href="${springMacroRequestContext.getContextPath()}/admin/"><b>Administration</b></a></li>
            <li><a href="${springMacroRequestContext.getContextPath()}/admin/schools"><b>Schools</b></a></li>
            <li class="active"><b>${school.name}</b></li>
        </ol>

    </div>
</@layout.vaultLayout>