import groovy.json.JsonSlurper
import java.util.List

def processData(message) {
    
    String body = message.getBody(String.class)
    JsonSlurper slurper = new JsonSlurper()
    def parsedJson = slurper.parseText(body)
    
    def products = parsedJson.Product
    if (products == null) {
        products = parsedJson.Products.Product
    }
    
    if (!(products instanceof List)) {
        products = [products]
    }
    
    String emailText = "Hello Team,\n\n"
    emailText = emailText + "Here is the automated daily report for products priced over 50 dollars:\n\n"
    emailText = emailText + "--------------------------------------------------\n"
    
    for (int i = 0; i < products.size(); i++) {
        def currentProduct = products[i]
        emailText = emailText + "Product ID: " + currentProduct.ProductID + "\n"
        emailText = emailText + "Name:       " + currentProduct.ProductName + "\n"
        emailText = emailText + "Unit Price: \$" + currentProduct.UnitPrice + "\n"
        emailText = emailText + "--------------------------------------------------\n"
    }
    
    emailText = emailText + "\nRegards,\nSAP CPI System"
    
    message.setBody(emailText)
    message.setHeader("EmailSubject", "Daily Price Alert: " + products.size() + " High-Value Products found")
    
    return message
}