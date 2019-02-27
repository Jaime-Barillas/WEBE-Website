<%! String title = "Dashboard"; %>
<%! String banner = "Dashboard Page"; %>

<%@ include file = "./header.jsp" %>

<h2 class="fg-accent-dark"> Hello! </h2>
<% Student student = (Student)session.getAttribute("user"); %>
<p>
  Hello <%= student != null ? student.getFullName() : "" %>! <br/>
  You're currently in the <%= student != null ? student.getProgramDescription() : "" %> program.
</p>

<%@ include file = "./footer.jsp" %>
