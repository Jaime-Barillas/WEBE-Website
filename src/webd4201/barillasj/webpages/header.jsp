<%@ page import = "webd4201.barillasj.*" %>
<%@ page import = "webd4201.barillasj.webpages.Util" %>
<%!
  String[][] linksByUserState = {
    {"login.jsp", "register.jsp"},
    {"dashboard.jsp", "update.jsp", "Logout"}
  };
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

  <title><%= title %></title>
  <link rel="stylesheet" type="text/css" href="css/style-light.css"/>
</head>
<body>
  <!-- Anchor to the top of the page for a 'scroll to top' link. -->
  <a name="top"></a>
  <div id="nav-bar">
    <a href="https://durhamcollege.ca/">
      <img src="./images/dc-logo.png" alt="DC Logo"/>
    </a>
    <h1><%= banner %></h1>
    <div id="nav-bar-links">
      <%
        if (session.getAttribute("user") == null) {
          out.println(Util.createNavBarLinks(linksByUserState[0]));
        } else {
          out.println(Util.createNavBarLinks(linksByUserState[1]));
        }
      %>
    </div>
  </div>