
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Indvited</title>
	<meta charset="UTF-8">


    <style>
     
        div {
        
           font-family: Helvetica;
           font-size: 12px;
           margin-left: 10%;
           margin-right: 10%;
           margin-top: 10%;
           margin-bottom: 5%;
           background-color: black;
           color: lightyellow;
           padding: 10px;
        }
  

    </style>
</head>

<body>
    <p style="margin-left:10%;">
      <table>
      <tr>
      <td>
         <img height="75px" width="75px" src="/images/ms-icon-150x150.png" alt="Indvited"/>
      </td>
      <td style="font-size:50px;color:yellow;text-shadow: 2px 2px lightgrey;text-align:centre">
         Indvited
      </td>
      </tr>
      </table>
    </p>
    
    <div>

      <p> <span style="font-size:50px;color:yellow;text-align:centre">${result}</span></p>

    </div>


</body>
</html>