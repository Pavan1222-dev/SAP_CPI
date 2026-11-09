import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    def body = message.getBody(String)
    
    message.setProperty("IsValid", "true")
    
    try {
        def slurper = new JsonSlurper()
        def order = slurper.parseText(body)
        
        def orderId = order.OrderID?.toString()?.trim()
        
        // This is the ONLY new line added to fix your Idempotent crash!
        message.setProperty("OrderID", orderId) 
        
        def customerId = order.CustomerID?.toString()?.trim()
        def productId = order.ProductID?.toString()?.trim()
        def quantityStr = order.Quantity?.toString()?.trim()
        
        if (!orderId || !customerId || !productId || !quantityStr) {
            message.setProperty("IsValid", "false")
            message.setProperty("ValidationError", "Missing or blank mandatory fields. Required: OrderID, CustomerID, ProductID, Quantity.")
            return message 
        }

        if (!quantityStr.isInteger() || quantityStr.toInteger() <= 0) {
            message.setProperty("IsValid", "false")
            message.setProperty("ValidationError", "Invalid Data: Quantity must be a numeric whole number greater than zero.")
            return message 
        }
        
    } catch (Exception e) {
        message.setProperty("IsValid", "false")
        message.setProperty("ValidationError", "Invalid JSON payload structure.")
    }
    
    return message
}