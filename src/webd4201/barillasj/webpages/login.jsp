<%! String title = "Login"; %>
<%! String banner = "Login Page"; %>

<%@ include file = "./header.jsp" %>

<h3 class="fg-danger"> <%= session.getAttribute("loginError") %> </h3>
<form method="post" action="./Login">
  <label for="studentId">Login Id:</label>
  <input type="text" id="studentId" name="studentId" value=""/>
  <label for="studentPassword">Password</label>
  <input type="text" id="studentPassword" name="studentPassword" value=""/>
  <input type="submit" value="Log In"/>
</form>

<%@ include file = "./footer.jsp" %>
