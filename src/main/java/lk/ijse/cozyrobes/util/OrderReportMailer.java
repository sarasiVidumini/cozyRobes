package lk.ijse.cozyrobes.util;


import lk.ijse.cozyrobes.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderReportMailer {

    public boolean sendLastOrderReport(String customerId) {
       /* try {
            // 1. Get last order id
            String lastCustomerId = getLastCustomerId();
            if (lastCustomerId == null) return false;

            // 2. Prepare Jasper report
            String jrxmlPath = "/report/Blank_A4_2.jrxml";
            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream(jrxmlPath)
            );
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_customer_id", lastCustomerId);

            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);

            // 3. Export to PDF

            String outPutDirectory = "reportPdf";
            File directory = new File(outPutDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it does not exist
            }
            String outputFilePath = outPutDirectory + "/order_report_" + lastCustomerId + LocalDate.now() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);

            // 4. Send email with attachment
            return EmailUtil.sendMailWithAttachment(
                    "shalukaofficial24@gmail.com",
                    "Order Report",
                    "Please find the attached order report for Order ID: " + lastCustomerId,
                    outputFilePath
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        try {
            String lastCustomerId = getLastCustomerId();
            if (lastCustomerId == null) return false;
            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/Blank_A4_2.jrxml")
            );
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_customer_id", customerId);
            parameters.put("PA_DATE", LocalDate.now().toString());

            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
            JasperViewer.viewReport(jasperPrint, false);

            // List
            // Array list
            // index value
            // 0 - ""
            // 1 - ""

            // Map
            // HashMap
            // key value
            // "hello" - "hi"

//            JasperFillManager.fillReport(
//                    report,
//                    null,
//                    connection
//            );

        } catch (Exception e) {
            e.printStackTrace();
        }
return true;
    }

    private static String getLastCustomerId() throws Exception {
        String sql = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
        try (
                ResultSet rs = CrudUtil.execute(sql)
        ) {
            if (rs.next()) {
                return rs.getString("customer_id");
            }
            return null;
 }
}
}
