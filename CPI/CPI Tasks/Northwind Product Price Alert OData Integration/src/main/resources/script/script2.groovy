import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    
    def exception = message.getProperty("CamelExceptionCaught")
    String errorDetail = exception != null ? exception.getMessage() : "Unknown Error"
    String failedSystem = "Unknown Integration Step"

    if (errorDetail.toLowerCase().contains("smtp") || errorDetail.toLowerCase().contains("mail") || errorDetail.toLowerCase().contains("socket")) {
        failedSystem = "Mail Adapter (Email Dispatch Failed)"
    } else if (errorDetail.toLowerCase().contains("odata") || errorDetail.toLowerCase().contains("http")) {
        failedSystem = "Northwind OData Service (Data Fetch Failed)"
    }

    String currentBody = message.getBody(String.class) ?: "No payload available"
    String safeBody = currentBody.replace('"', '\\"').replace('\n', '\\n').replace('\r', '')

    String errorReport = """{
        "Status": "Failed",
        "FailedSystem": "${failedSystem}",
        "ErrorDetail": "${errorDetail.replace('"', '\\"')}",
        "FailedPayload": "${safeBody}"
    }"""

    message.setBody(errorReport)
    String timestamp = new Date().format("yyyyMMdd_HHmmss")
    message.setProperty("DataStoreEntryID", "Failed_Alert_" + timestamp)

    return message
}