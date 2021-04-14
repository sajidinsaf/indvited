
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Indvited</title>
	<meta charset="UTF-8">

    <script language="javascript" type = "text/javascript">
<%--    function init() {
    var submitButton = document.getElementById('submitRequest').onclick
	    submitButton.onClick = function() {
    	  sendData();
	    }
    } --%>
    function sendData() {

        let result = document.querySelector('.result');
        result.innerHTML = "";
        let eventId = document.getElementById('eventId');

        let firstName = document.querySelector('#firstName');
        if (match(firstName, "#firstNameValMsg", /^[A-Za-z]+$/, "First name can only have alphabets")){
        } else {
        	return;
        }
  
        let lastName = document.querySelector('#lastName');
        if (match(lastName, "#lastNameValMsg", /^[A-Za-z]+$/, "Last name can only have alphabets")){
        } else {
        	return;
        }
        
        let email = document.querySelector('#email');
        if (match(email, "#emailValMsg", /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/, "Not a valid email address!")){
        } else {
        	return;
        }
        
        let phone = document.querySelector('#phone');
        if (match(phone, "#phoneValMsg", /^[7-9]{1}[0-9]{9}$/, "Enter a 10 digit number starting with 7, 8 or 9")){
        } else {
        	return;
        }
        
        let address = document.querySelector('#address');
        if (match(address, "#addressValMsg", /^[a-zA-Z0-9\s,. '-]{5,}$/, "Not a valid address")){
        } else {
        	return;
        }
 
        let city = document.querySelector('#city');
        if (match(city, "#cityValMsg", /^[a-zA-Z\s ]{3,}$/, "Enter a valid city name")){
        } else {
        	return;
        }
        
        let seletedDate = document.querySelector('#selectedDate');
        let seletedTime = document.querySelector('#selectedTime');
        
        // Creating a XHR object
        let xhr = new XMLHttpRequest();
        let url = "/api/v1/event/subscribeScheduleWebForm";
    
        // open a connection
        xhr.open("POST", url, true);

        // Set the request header i.e. which type of content you are sending
        xhr.setRequestHeader("Content-Type", "application/json");

        // Create a state change callback
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {

                // Print received data from server
                result.innerHTML = this.responseText;

            }
        };
        var selectedDateOption = seletedDate.options[seletedDate.selectedIndex].text;
        var selectedTimeOption = seletedTime.options[seletedTime.selectedIndex].text;
        
        var criteria = "";
        
        <c:forEach items="${event.eventCriteria}" var="criterion">
           var inputFieldName = "#criterionId${criterion.id}";
           var inputFieldNameValMsg = "#criterionId${criterion.id}ValMsg";
           var inputFieldNameForJson = "criterionId${criterion.id}";
           criteria=criteria+"criterionSep" + inputFieldNameForJson +"nameValSep"+document.querySelector(inputFieldName).value;
           if (match(document.querySelector(inputFieldName), inputFieldNameValMsg, /^[1-9]{1}[0-9]{0,9}$/, "Enter a number")){
           } else {
           	return;
           }
        </c:forEach>
    
        let gender = document.querySelector('#gender');
        var selectedGender = gender.options[gender.selectedIndex].text;
        
        // Converting JSON data to string
        var data = JSON.stringify({ "eventId": eventId.value, "firstName": firstName.value, "lastName": lastName.value, "email": email.value, "phone": phone.value, "address": address.value, "city": city.value, "preferredDate": selectedDateOption, "preferredTime": selectedTimeOption, "criteria": criteria, "gender": selectedGender });

         console.log(data);
        // Sending data with the request
        xhr.send(data);
    	

    }
    
    function match(inputtxt, fieldName, pattern, msg) {
     if(inputtxt.value.match(pattern)) {
    	document.querySelector(fieldName).innerHTML = "";
        return true;
       }
     else {
    	 document.querySelector(fieldName).innerHTML = msg;
         return false;
       }
    }
    
    <%--
    document.addEventListener('readystatechange', function() {
	    if (document.readyState === "complete") {
	      init(); 
	    }
  	});
    --%>    
    </script>

    <style>
        h5 {
            <%-- text-align: center; --%>
        }
        h2 {
            <%-- text-align: center; --%>
            color: palevioletred;
        }
        
        .left {
		    float:left;
		}
		
		.right {
		    float:right;
		}
        
        div {
        
           font-family: Helvetica;
           font-size: 12px;
           margin-left: 10%;
           margin-right: 10%;
           margin-top: 3%;
           margin-bottom: 5%;
           background-color: black;
           color: lightyellow;
           padding: 10px;
        }
  
 <%--       img {
            display: block;
            margin: auto;
            height: 150px;
            width: 150px;
        } --%>
  
        .input {
            margin: 6px;
            padding: 10px;
            display: block;
            margin: auto;
            color: palevioletred;
            font-size: 30px;
        }
  
        input {
            width: 90%;
            display: block;
            margin-left: 12px;
            background: none;
            background-color: lightyellow;
            width: 250px;
        }
  
        select {
            display: block;
            margin-left: 12px;
            background: none;
            background-color: lightyellow;
        }
        
        #select_date {
            display: block;
            margin-left: 12px;
            background: none;
            background-color: lightyellow;
            width: 30px;
        }
  
        #heading {
            font-family: cursive;
            text-align: center;
            color: green;
            padding-top: 20px;
  
        }
  
        #form_page {
            height: 1000px;
            width: 50px;
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            margin-left: 50%;
            margin: auto;
		    background-color: #ffffff;
		    border: 1px solid black;
		    opacity: 0.6; 
        }
  
        #form_body {
            border-radius: 12px;
            height: 330px;
            width: 450px;
            background-color: beige;
            border: 1px solid pink;
            margin: auto;
            margin-top: 12px;
        }
  
        #text {
            color: red;
            width: 100px;
        }
  
        #head {
            border-bottom: 2px solid red;
            height: 100px;
            background-color: aliceblue;
        }
  
        #submit {
            width: 70px;
            background-color: lightyellow;
        }
    </style>
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

 
      <table style="margin-left:10%;margin-right:10%;width:100%">
      <tr>
      <td>
         <img height="75px" width="75px" src="/images/ms-icon-150x150.png" alt="Indvited"/>
      </td>
      <td style="font-size:50px;color:yellow;text-shadow: 2px 2px lightgrey;text-align:centre">
         Indvited
      </td>
      </tr>
      </table>
      <hr style="margin-left:10%;margin-right:10%"/>
    
      <table style="margin-left:10%;margin-right:10%;width:90%">
      <tr>
      <td style="font-size:16px;text-align:centre;">
         <a href="http://secure.codeczar.co.uk/api/vi/downloadApp">Download the Indvited App</a> and avail the benefits like automatic event notifications, profile management, networking and many others.  
      </td>
      </tr>
      </table>
    <div>

            <p>
            <c:if test="${not empty event}">
             Dear Blogger, apply for Indvited Event at:
                <h2>${event.eventVenue.name}, ${event.eventVenue.address}, ${event.eventVenue.city} </h2>
                           
                Event Type: ${event.eventType.name}
                <br /><br />
                
                Cap: ${event.cap} 
                <br /><br/>
                
                Notes. ${event.notes} 
                <br />
                <br/>       
                Exclusions: ${event.exclusions} 
                <br /> <br/>

                Eligibility Criteria: 
                <ul>
	                <c:forEach items="${event.eventCriteria}" var="criterion">
	                    <li>${criterion.forum.name}: ${criterion.criterionValue}+ ${criterion.name}</li>
	                </c:forEach>
                </ul>
                <br/>
                Deliverables: 
                <ul>
	                <c:forEach items="${event.eventDeliverables}" var="deliverable">
	                    <li>${deliverable.deliverableName}</li>
	                </c:forEach>
                </ul>    
                <br/> 

                PR Name: ${event.member.firstName} ${event.member.lastName}                
                <br/>         
                <br/>
<hr/>
                
                  <input type="hidden" id="eventId"  name="eventId" value="${event.id}" />
                   <table>
                     <tr><td>First Name:</td><td><input type="text" name="firstName" id="firstName"/><td style="color:red" id="firstNameValMsg" name="firstNameValMsg"></td></tr>
                     <tr><td>Last Name:</td><td><input type="text" name="lastName" id="lastName"/></td><td style="color:red" id="lastNameValMsg" name="lastNameValMsg"></td></tr>
                     <tr><td>Email Id:</td><td><input type="text" name="email" id="email"/></td><td style="color:red" id="emailValMsg" name="emailValMsg"></td></tr>
                     <tr><td>Mobile Number:</td><td><input type="text" name="phone" id="phone"/></td><td style="color:red" id="phoneValMsg" name="phoneValMsg"></td></tr> 
                     <tr><td>Address:</td><td><input type="text" name="address" id="address"/></td><td style="color:red" id="addressValMsg" name="addressValMsg"></td></tr>
                     <tr><td>City:</td><td><input type="text" name="city" id="city"/></td><td style="color:red" id="cityValMsg" name="cityValMsg"></td></tr>
                     
                     <tr><td>Gender </td><td><select name="gender" id="gender"><option>F</option><option>M</option></select></td><td/></tr>

	                <c:forEach items="${event.eventCriteria}" var="criterion">
	                    <tr>
	                    <td>${criterion.forum.name} ${criterion.name}</td>
	                    <td><input type="text" name="criterionId${criterion.id}" id="criterionId${criterion.id}"/></td>
	                    <td style="color:red" name="criterionId${criterion.id}ValMsg" id="criterionId${criterion.id}ValMsg"></td>
	                    </tr>
	                </c:forEach>

                     
                     <tr><td>Select Preferred Date: </td>
                       <td> 
		                   <select name="selectedDate" id="selectedDate">
			                  <c:forEach items="${event.allAvailableScheduledDatesForDisplay}" var="availableDate">
			                    <option>${availableDate.date}</option>
			                    <br/>
			                  </c:forEach>
		                   </select>
	                   </td>
	                   <td/>
	                   </tr>

                     <tr>
                     	 <td>Select Preferred Time:</td>
                     	 <td> 
			                  <select name="selectedTime" id="selectedTime">
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
                  		</td>
                  		<td/>
                  	</tr>
                  </table>
                  
                  <p class="result" style="color:green;background-color:white;padding-left:2%;font-size:16px"></p>
                  <br/>
                  <br/>
                  <input type="submit" name="submitRequest" id="submitRequest" value="Submit Request" onclick="sendData();"/>

          </c:if>
                </p>  
          <br/>
       <hr/>
          <c:choose>
	          <c:when test="${empty event}">
	              Error occurred fetching event details. Please contact indvited@codeczar.co.uk. Quote 'Event Error' in the subject and send us the event link you received.
	          </c:when>
	          <c:otherwise>
	              For any queries, please contact indvited@codeczar.co.uk. Provide your email id or phone number so that we can revert back to you with our response.
	          </c:otherwise>
          </c:choose>
       <hr/>        
           

    </div>


</body>
</html>