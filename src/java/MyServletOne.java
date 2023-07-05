
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(urlPatterns = {"/MyServletOne"})
public class MyServletOne    extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter prt = res.getWriter();
        String emp_id, emp_name, emp_salary;
        emp_id = req.getParameter("t1");
        emp_name = req.getParameter("t2");
        emp_salary= req.getParameter("t3");
//        prt.println("id = "+emp_id);
//        prt.println("name = "+emp_name);
//        prt.println("salary = "+emp_salary);


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c1;
            c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbemp?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "root");
            Statement st = c1.createStatement();
            String emp_data = "insert into emp_table values("+emp_id+",' "+emp_name+" ',"+emp_salary+" )";
            boolean b1 = st.execute(emp_data);
            prt.println(b1);

            st.close();
            c1.close();
        } catch (Exception e) {
            prt.println(e);
        }

    }

}
