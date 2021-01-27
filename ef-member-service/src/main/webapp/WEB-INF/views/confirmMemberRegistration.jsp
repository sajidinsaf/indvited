
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Indvited</title>


<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>
<!--
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Spring 3 MVC Project @JavaConfig</a>
    </div>
  </div>
</nav>
   -->
<body>
	<div class="jumbotron">
		<div class="container">

			<h1>${title}</h1>
			<p>
				<c:if test="${not empty member}">
            Dear ${member.firstName} <br />
					<br />
				Thank you for registering at Indvited <br />
									<br />
				
				Your registration is enabled<br />
									<br />
				
				Your member id is: ${member.id} <br />
									<br />
				
				Please quote this member id when communicating with customer services. <br />
									<br />
				
				Your username is: ${member.username} <br />
									<br />
				
				You can now login to the Invited Application <br />
									<br />
				
				For any queries, please contact indvited@codeczar.co.uk. Quote your member id in the subject.
          </c:if>

				<c:if test="${empty member}">
            Registration failed. Incorrect or expired confirmation code. <br />
					<br />
            Please contact indvited@codeczar.co.uk. Quote your email id in the subject.
        </c:if>
			</p>
			<!--    <p>
        <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
    </p>
 -->
		</div>
	</div>


	<hr>


	<!-- 
<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />
 
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
-->

</body>
</html>