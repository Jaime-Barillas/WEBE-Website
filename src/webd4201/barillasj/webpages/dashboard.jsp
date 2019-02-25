<%! String title = "Dashboard"; %>
<%! String banner = "Dashboard Page"; %>

<%@ include file = "./header.jsp" %>

<h2 class="fg-accent-dark"> Hello! </h2>
<%= session.getAttribute("msg") %>

<%@ include file = "./footer.jsp" %>
