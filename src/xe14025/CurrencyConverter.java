package xe14025;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/currencyconverter")

public class CurrencyConverter extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		
		ArrayList<String> curr = new ArrayList<String>();
		HashMap<String, String> toCode = new HashMap<String, String>();
		HashMap<String, Integer> toValue = new HashMap<String, Integer>();
		
		  curr.add("EUR");
		  curr.add("USD");
		  curr.add("GBP");
		  
		  toCode.put("EUR", "Ευρώ");
		  toCode.put("USD", "Δολάριο ΗΠΑ");
		  toCode.put("GBP", "Λίρα Αγγλίας");
		 
		  toValue.put("Ευρώ", 100);
		  toValue.put("Δολάριο ΗΠΑ", 106);
		  toValue.put("Λίρα Αγγλίας", 84);
		  
		boolean buttonPressed; boolean badValues = false;
		double amount=0;
		int from = 0, to = 0 ;
		String amountParam, fromParam, toParam ;
		
		buttonPressed = (request.getParameter("metatropi") != null);
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
		
		if (buttonPressed) {
			try {
				amount = Double.parseDouble(amountParam)*100;
				} catch (NumberFormatException e) 
					{ badValues = true;
				}
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); 
		out.println("<!DOCTYPE html>\n" + "<html>\n"
				+ "<head><title>Μετατροπή Νομισμάτων</title></head>\n"
				+ "<body bgcolor=\" #e6ffe6\n" + "\">\n" + "<h1>Μετατροπή Ποσών σε Διαφορετικά Νομίσματα</h1>\n");
		
		out.println("<form>\n"
				+ "Ποσό<input type=\"text\" name=\"amount\" value=\""
				+ (amountParam == null ? "" : amountParam)
				+ "\" />"
				+ "Απο:"
				+ "<select name=\"from\">");
			for (String currency:curr) {
			  out.println("<option value=\"" + currency + "\"" + (Objects.equals(currency, fromParam) ? " selected" : "")
					  + ">" + toCode.get(currency) + "</option>");
			  
			}
			out.print("</select>"
				+ "Σε:"
				+ "<select name=\"to\">" );
				for (String currency:curr) {
					  out.println("<option value=\"" + currency + "\"" + (Objects.equals(currency, toParam) ? " selected": "")
							  + ">" + toCode.get(currency) + "</option>");
				  }
				  out.print("</select>"
				+ "<input type=\"submit\" name=\"metatropi\" value=\"Μετατροπή\" /><br>\n"
				+ "</form>");
				
		if (buttonPressed) { 
			
			if (badValues) { 
			out.println("<strong style=\"color:red\">Λάθος! Προσπάθησε ξανά</strong>");
			} else {
				out.println("<h2>" + printResult(amount, from, to) + "</h2> \n" );
				
			}
		}
		
		out.println("</body></html>");
				
	}
	 
	 
	private String printResult(double amount, int from, int to) {
		 int result = (int) ((amount/from)*to);
		  return new DecimalFormat("0.##").format(result*0.01);
	}
	
}

