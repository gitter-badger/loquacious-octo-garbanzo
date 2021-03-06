<#import "../common/dashboard_master.ftl" as layout />
<@layout.dashboardTemplate title="Incident Details">

<h1>${incident.title}</h1>
<p>Opened ${incident.createdAt}; last updated ${incident.updatedAt}</p>

<p>Affected Services:</p>
<ul>
	<#list incident.affectedServiceIds as serviceId>
		<li>${nameForServiceId(serviceId)}</li>
	</#list>
</ul>

<p>Updates:</p>
<ul>
	<#list incident.incidentUpdates as update>
		<li>${update.description}</li>
	</#list>
<ul>
</@layout.dashboardTemplate>
