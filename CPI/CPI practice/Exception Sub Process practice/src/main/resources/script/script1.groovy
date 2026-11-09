package script
import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

  throw new Exception("Theres an error please check your iflow")
    return message;
}