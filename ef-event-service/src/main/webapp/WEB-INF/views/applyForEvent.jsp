
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
                <c:if test="${not empty event}">
            Dear Blogger, 
            <br />
            <br />
                Apply for Indvited Event at: ${event.eventVenue.name}, ${event.eventVenue.address}, ${event.eventVenue.city} 
                <br />
                <br />
                
                Event Type: ${event.eventType.name}
                <br />
                <br />
                
                Cap: ${event.cap} 
                <br />
                <br />
                
                Notes. ${event.notes} 
                <br />
                <br />
                
                Exclusions: ${event.exclusions} 
                <br />
                <br />

                Eligibility Criteria: <br />
                <ul>
                <c:forEach items="${event.eventCriteria}" var="criterion">
                    <li>${criterion.forum.name}: ${criterion.criterionValue}+ ${criterion.name}</li>
                    <br/>
                </c:forEach>
                </ul>
                <br/><br/>
                Deliverables: <br />
                <ul>
                <c:forEach items="${event.eventDeliverables}" var="deliverable">
                    <li>${deliverable.deliverableName}</li>
                </c:forEach>
                </ul>      
                <br/><br/>          


                <form action="https://secure.event.codeczar.co.uk/api/v1/event/subscribeSchedule" method="post">  
                  
                  <input type="hidden" name="eventId" value="${event.id}" /><br/><br/>  
                   Name:<input type="text" name="userName"/><br/><br/>   
                   Email Id:<input type="text" name="userEmail"/><br/><br/>  
                   Phone: <input type="text" name="phone"/><br/><br/>  
                   Select Preferred Date:  
                   <select name="selectedDate">
	                  <c:forEach items="${event.allAvailableScheduledDatesForDisplay}" var="availableDate">
	                    <option>${availableDate.date}</option>
	                    <br/>
	                  </c:forEach>
                   </select>

                   Select Preferred Time: 
                  <select name="selectedDate">
                      <option>1000</option>
                      <option>1100</option>
                      <option>1200</option>
                      <option>1300</option>
                      <option>1400</option>
                      <option>1500</option>
                      <option>1600</option>
                      <option>1700</option>
                      <option>1800</option>
                      <option>1900</option>
                  </select>
                  
                <br/><br/>  
                <input type="submit" value="Submit Request"/>  
  
</form>  

          </c:if>

          <c:choose>
	          <c:when test="${empty event}">
	              Error occurred fetching event details. Please contact indvited@codeczar.co.uk. Quote 'Event Error' in the subject and send us the event link you received.
	          </c:when>
	          <c:otherwise>
	              For any queries, please contact indvited@codeczar.co.uk. Provide your email id or phone number so that we can revert back to you with our response.
	          </c:otherwise>
          </c:choose>
          
           
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