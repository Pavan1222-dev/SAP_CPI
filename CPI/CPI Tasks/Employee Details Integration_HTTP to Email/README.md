# Employee Details Integration: HTTP to Email

An integration task that accepts employee JSON over HTTP, validates and processes the payload in SAP Cloud Integration, and sends the employee details to HR by email.

The iFlow includes an HTTP sender, mail receiver, Groovy processing, and an exception subprocess for error responses. Configure endpoint and mail credentials through secure SAP Integration Suite configuration before deployment.
