
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(urlPatterns = {"/MyServletOne"})
public class MyServletOne extends HttpServlet {

    Connection c1;
    Statement st;
    PrintWriter prt;

    public void init(ServletConfig config) throws ServletException {
        //initialization code

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?zeroDateTimeBehavior"
                    + "=CONVERT_TO_NULL", "root", "root");
            st = c1.createStatement();
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        prt = res.getWriter();
        String emp_id, emp_name, emp_salary,edit_id;
        String strselect = "";
        edit_id= req.getParameter("Edit_id");
        emp_id = req.getParameter("t1");
        emp_name = req.getParameter("t2");
        emp_salary = req.getParameter("t3");
        strselect = req.getParameter("select");

        if (strselect.equals("add") == true) {
            try {
                String emp_add = "insert into emp_table values(" + emp_id + ",' " + emp_name + " '," + emp_salary + " )";
                boolean b1 = st.execute(emp_add);
                if (b1 == false) {
                    prt.println("<h1>Item's Added SuccessFully....</h1>");
                } else {
                    prt.println("<h1>Item's Not  Added , Some Error pls  Check...</h1>");
                }
            } catch (Exception e) {
                prt.println(e);
            }
        }//add   if    
        else if (strselect.equals("edit") == true) {
            try {

                String emp_edit = "update emp_table set  Emp_id="+emp_id+",Emp_name='"+emp_name+"',Emp_salary="+emp_salary+"    where Emp_Id=" + edit_id + " ";

                boolean b1 = st.execute(emp_edit);
                if (b1 == false) {
                    prt.println("<h1>Item's update SuccessFully....</h1>");
                } else {
                    prt.println("<h1>Item's Does Not  updateSome Error pls  Check...</h1>");
                }

            } catch (Exception e) {
            }

        } //edit    if  
        else if (strselect.equals("delete") == true) {
            try {

                String e_del = "DELETE  FROM emp_table  WHERE Emp_Id = " + emp_id + "  ";

                boolean b1 = st.execute(e_del);

                if (b1 == false) {
                    prt.println("<h1>Row Id Number is : " + emp_id + " Has Been Deleted....</h1>");
                } else {
                    prt.println("<h1>Row  Not Delete Some Error pls  Check...</h1>");
                }
            } catch (Exception e) {
            }

        }//delete if
        else if (strselect.equals("show") == true) {
           
            prt.print("<style>table tr td{border:2px solid blue;background-color:#FF6BCC55;}"
                    + "th{border:2px solid red;background-color:yellow;}</style>");
                    
            prt.print("<table>");
            try {
         ResultSet rs = st.executeQuery("select * from  emp_table  ");
         prt.print("<tr><th>Employee_Id</th><th>Employee_Name</th><th>Employee_Salary</th></tr>");
                while (rs.next() == true) {
                        prt.print("<tr><td>" + rs.getInt(1) + " </td>"
                                + "<td> " + rs.getString(2) + " </td>"
                                + "<td> " + rs.getInt(3) + "</td></tr><br>");
                }
            prt.print("</table>");
            } catch (Exception e) {
            } 
        }
    }//show if

    public void destroy() {
        prt.print("in destroy" + "<br>");

        try {
            st.close();
            c1.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyServletOne.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
