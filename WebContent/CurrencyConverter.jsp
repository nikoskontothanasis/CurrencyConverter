<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.text.DecimalFormat" %>
<%  

ArrayList<String> curr = new ArrayList<String>();
HashMap<String, String> toCode = new HashMap<>();
HashMap<String, Integer> toValue = new HashMap<>();
boolean badValues=false;
String amountParam, fromParam, toParam;
double amount = 0;
int from=0, to=0;

curr.add("EUR");
curr.add("USD");
curr.add("GBP");

toCode.put("EUR", "Ευρώ");
toCode.put("USD", "Δολάριο ΗΠΑ");
toCode.put("GBP", "Λίρα Αγγλίας");

toValue.put("Ευρώ", 100);
toValue.put("Δολάριο ΗΠΑ", 106);
toValue.put("Λίρα Αγγλίας", 84);

amountParam = request.getParameter("amount");
fromParam = request.getParameter("from");
toParam = request.getParameter("to"); 

try {
	  from = toValue.get(toCode.get(fromParam));
	  to = toValue.get(toCode.get(toParam));
} catch (NullPointerException e) {
	  from = 100;
	  to = 100;
}

if (request.getParameter("metatropi") != null) {
	  try {
		  amount = Double.parseDouble(amountParam)*100;
	  } catch (NumberFormatException e) {
		  badValues = true;
	  }
}

%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Currency Converter</title>
</head>
<body bgcolor= #e6ffe6>
<h1 align=center> Μετατροπή Ποσών Σε Διαφορετικά Νομίσματα </h1>
<form>
Ποσό <input type=text name=amount value='<%= amountParam == null ? "" : amountParam %>' />
Από: <select name=from>
<%
for (String currency:curr) {
	%>
	<option value='<%= currency %>' <%= Objects.equals(currency, fromParam) ? " selected" : "" %> >
		<%= toCode.get(currency) %> </option>
	<%
}
%>
</select>
Σε: <select name=to>
<%
for (String currency:curr) {
	%>
	<option value='<%= currency %>' <%= Objects.equals(currency, toParam) ? " selected" : "" %> >
		<%= toCode.get(currency) %> </option>
	<%
}
%>
</select>
<input type=submit name=metatropi value=Μετατροπή />
</form>
<%
if (request.getParameter("metatropi") != null) {
	if (badValues) {
		%>
		<h1><strong style=color:red>Λάθος! Προσπάθησε ξανά</strong></h1>
	<%
	} else {
		%>
		<h3><%= printResult(amount, from, to) %></h3>
		<%
	}
}
%>
</body>
</html>
<%! 
private String printResult(double value, int from, int to) {
	int result = (int) ((value/from)*to);
	return new DecimalFormat("0.##").format(result*0.01);
}
%>