# Northwind Product Price Alert OData Integration

An integration task that reads product data from the Northwind OData service, selects products whose `UnitPrice` is greater than 50, and emails a price alert.

The iFlow includes Groovy processing and an exception path that records failed alerts. Configure service access and mail credentials securely before deployment.
