USE master;

CREATE TABLE [OrderDetail](
	orderDetailID INT IDENTITY(1, 1) PRIMARY KEY,
	productID VARCHAR(8),
	unitPrice FLOAT,
	quantity INT,
	total FLOAT, 
	orderID VARCHAR(5)
)

/*
CREATE FUNCTION calculateTotalOrder(
    @orderId VARCHAR(5)
)
RETURNS FLOAT
AS
BEGIN
    DECLARE @total FLOAT;
 
    SELECT @total = SUM(total) 
    FROM [OrderDetail]
    WHERE orderID = @orderId;

    RETURN ISNULL(@total, 0);
END;
*/

CREATE SEQUENCE ID_sequence
AS INT
START WITH 1
INCREMENT BY 1 
MAXVALUE 999
NO CYCLE

CREATE TABLE [Order] (
	orderID VARCHAR(5) NOT NULL PRIMARY KEY,
	orderDate DATETIME NOT NULL,
	userAddress NVARCHAR(250) NOT NULL,
	total FLOAT
)

ALTER TABLE [OrderDetail]
	ADD CONSTRAINT FK_Order_OrderDetail FOREIGN KEY (orderID)
	REFERENCES [Order](orderID)

CREATE TRIGGER FormatIDTrigger
ON [Order]
INSTEAD OF INSERT 
AS
BEGIN
	DECLARE @NewID VARCHAR(5)
	SET @NewID = 'HD' + FORMAT(NEXT VALUE FOR ID_sequence, '000')

	INSERT INTO [Order] (orderID, orderDate, userAddress, total)
	SELECT @NewID, orderDate, userAddress, total
    FROM inserted;
END