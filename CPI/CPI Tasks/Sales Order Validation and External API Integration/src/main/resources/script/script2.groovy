import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def exception = message.getProperty("CamelExceptionCaught")
    
    int statusCode = 500
    String errorCategory = "Internal Server Error"
    String Detail = "An unknown integration error occurred."

    if (exception != null) {
        Detail = exception.getMessage()
        if (Detail && Detail.contains("statusCode:")) {
            def matcher = Detail =~ /statusCode:\s*(\d{3})/
            if (matcher.find()) {
                statusCode = matcher[0][1].toInteger()
            }
            errorCategory = " API Rejected Request"
            
        } else if (Detail && (Detail.toLowerCase().contains("timeout") || Detail.contains("SocketTimeoutException"))) {
            statusCode = 504
            errorCategory = "Gateway Timeout - Target system took too long to respond"
            
        } else if (Detail && Detail.contains("401")) {
            statusCode = 401
            errorCategory = "Unauthorized - Integration authentication failed"
        }
    }
    
    message.setHeader("CamelHttpResponseCode", statusCode)
    
    message.setProperty("finalErrorCode", statusCode)
    message.setProperty("finalErrorCategory", errorCategory)
    message.setProperty("finalErrorDetail", Detail)
    
    return message
}