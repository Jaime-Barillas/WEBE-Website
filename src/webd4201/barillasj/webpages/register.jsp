<%! String title = "Register"; %>
<%! String banner = "Register Page"; %>

<%@ include file = "./header.jsp" %>

<%
  if (session.getAttribute("user") != null) {
    response.sendRedirect("./dashboard.jsp");
  }

  String firstName = Util.getSessionAttr(session, "registerFirstName", "");
  String lastName = Util.getSessionAttr(session, "registerLastName", "");
  String password = Util.getSessionAttr(session, "registerPassword", "");
  String confirmPassword = Util.getSessionAttr(session, "registerConfirmPassword", "");
  String email = Util.getSessionAttr(session, "registerEmail", "");
  String programCode = Util.getSessionAttr(session, "registerprogramCode", "");
  String programDescription = Util.getSessionAttr(session, "registerprogramDescription", "");
  String year = Util.getSessionAttr(session, "registerYear", "");
%>

<h2 class="fg-accent-dark">
  <%
    Object registerMsg = session.getAttribute("registerMsg");
    if (registerMsg != null) {
      out.println(registerMsg);
      session.removeAttribute("registerMsg");
    }
  %>
</h2>

<form method="post" action="./Register">
  <div class="row-wrap">
    <label class="width-5" for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= firstName %>"/>

    <label class="width-5" for="password">Password:</label>
    <input class="" type="password" id="password" name="password" value="<%= password %>"/>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="lastName">Last Name:</label>
    <input class="" type="text" id="lastName" name="lastName" value="<%= lastName %>"/>

    <label class="width-5" for="confirmPassword">Confirm Password:</label>
    <input class="" type="password" id="confirmPassword" name="confirmPassword" value="<%= confirmPassword %>"/>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="email">Email:</label>
    <input class="" type="text" id="email" name="email" value="<%= email %>"/>

    <label class="width-5" for="programCode">Program Code:</label>
    <input class="" type="text" id="programCode" name="programCode" value="<%= programCode %>"/>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="year">Year of Study:</label>
    <input class="" type="text" id="year" name="year" value="<%= year %>"/>

    <label class="width-5" for="programDescription">Program Description:</label>
    <input class="" type="text" id="programDescription" name="programDescription" value="<%= programDescription %>"/>
  </div>

  <input type="submit" value="Register"/>
</form>

<%@ include file = "./footer.jsp" %>
