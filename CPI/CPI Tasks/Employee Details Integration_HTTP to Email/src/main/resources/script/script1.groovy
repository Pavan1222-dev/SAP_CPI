import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {
    
    def body = message.getBody(String)
    def jsonSlurper = new JsonSlurper()
    def payload = jsonSlurper.parseText(body)
    
   
    if (!payload.employeeId || !payload.name || !payload.department || !payload.email) {
        throw new Exception("Validation Failed: Missing required fields. Ensure employeeId, name, department, and email are provided.")
    }
    
    
    if (!(payload.employeeId ==~ /^EMP\d+$/)) {
        throw new Exception("Validation Failed: Invalid employeeId format. It must start with 'EMP' followed by numbers (e.g., EMP001).")
    }
    
    message.setProperty("empId", payload.employeeId)
    message.setProperty("empName", payload.name)
    message.setProperty("empDept", payload.department)
    message.setProperty("empEmail", payload.email)
    
    return message
}