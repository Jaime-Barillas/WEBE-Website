<%! String title = "Password Update"; %>
<%! String banner = "Password Update"; %>

<%@ include file = "./header.jsp" %>

<%
  if (session.getAttribute("user") == null) {
    session.setAttribute("loginError", "You must be logged in to update your password!");

    response.sendRedirect("./login.jsp");
  }
%>

<h3 class="fg-danger">
  <%
    Object updatePasswordMsg = session.getAttribute("updatePasswordMsg");
    if (updatePasswordMsg != null) {
      out.println(updatePasswordMsg);
      session.removeAttribute("updatePasswordMsg");
    }
  %>
</h3>
<form method="post" action="./UpdatePassword">
  <label for="oldPassword">Old Password:</label>
  <input type="password" id="oldPassword" name="oldPassword" value=""/> <br/>
  <label for="newPassword">New Password:</label>
  <input type="password" id="newPassword" name="newPassword" value=""/> <br/>
  <label for="confirmPassword">Confirm New Password:</label>
  <input type="password" id="confirmPassword" name="confirmPassword" value=""/> <br/>
  <input type="submit" value="Update Password"/>
</form>

<%@ include file = "./footer.jsp" %>
