<%! String title = "Register"; %>
<%! String banner = "Register Page"; %>

<%@ include file = "./header.jsp" %>

<%
  if (session.getAttribute("user") != null) {
    response.sendRedirect("./dashboard.jsp");
  }

  String id = Util.getSessionAttr(session, "registerId", "");
  String idErr = Util.getSessionAttr(session, "registerIdErr", "");
  String firstName = Util.getSessionAttr(session, "registerFirstName", "");
  String firstNameErr = Util.getSessionAttr(session, "registerFirstNameErr", "");
  String lastName = Util.getSessionAttr(session, "registerLastName", "");
  String lastNameErr = Util.getSessionAttr(session, "registerLastNameErr", "");
  String password = Util.getSessionAttr(session, "registerPassword", "");
  String passwordErr = Util.getSessionAttr(session, "registerPasswordErr", "");
  String confirmPassword = Util.getSessionAttr(session, "registerConfirmPassword", "");
  String confirmPasswordErr = Util.getSessionAttr(session, "registerConfirmPasswordErr", "");
  String email = Util.getSessionAttr(session, "registerEmail", "");
  String emailErr = Util.getSessionAttr(session, "registerEmailErr", "");
  String programCode = Util.getSessionAttr(session, "registerProgramCode", "");
  String programCodeErr = Util.getSessionAttr(session, "registerProgramCodeErr", "");
  String programDescription = Util.getSessionAttr(session, "registerProgramDescription", "");
  String programDescriptionErr = Util.getSessionAttr(session, "registerProgramDescriptionErr", "");
  String year = Util.getSessionAttr(session, "registerYear", "");
  String yearErr = Util.getSessionAttr(session, "registerYearErr", "");
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
  <div class="row-nwrap">
    <span class="fg-danger width-3"><%= idErr %></span>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="id">Student Id:</label>
    <input type="text" id="id" name="id" value="<%= id %>"/>
  </div>
  <div class="row-nwrap">
    <span class="fg-danger width-3"><%= firstNameErr %></span>
    <span class="fg-danger"><%= passwordErr %></span>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= firstName %>"/>

    <label class="width-5" for="password">Password:</label>
    <input class="" type="password" id="password" name="password" value="<%= password %>"/>
  </div>
  <div class="row-nwrap">
    <span class="fg-danger width-3"><%= lastNameErr %></span>
    <span class="fg-danger"><%= confirmPasswordErr %></span>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="lastName">Last Name:</label>
    <input class="" type="text" id="lastName" name="lastName" value="<%= lastName %>"/>

    <label class="width-5" for="confirmPassword">Confirm Password:</label>
    <input class="" type="password" id="confirmPassword" name="confirmPassword" value="<%= confirmPassword %>"/>
  </div>
  <div class="row-nwrap">
    <span class="fg-danger width-3"><%= emailErr %></span>
    <span class="fg-danger"><%= programCodeErr %></span>
  </div>
  <div class="row-wrap">
    <label class="width-5" for="email">Email:</label>
    <input class="" type="text" id="email" name="email" value="<%= email %>"/>

    <label class="width-5" for="programCode">Program Code:</label>
    <input class="" type="text" id="programCode" name="programCode" value="<%= programCode %>"/>
  </div>
  <div class="row-nwrap">
    <span class="fg-danger width-3"><%= yearErr %></span>
    <span class="fg-danger"><%= programDescriptionErr %></span>
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
