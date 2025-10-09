USE master;

CREATE TABLE [Registration](
	username NVARCHAR(20) NOT NULL PRIMARY KEY,
	[password] NVARCHAR(20),
	lastName NVARCHAR(100),
	isAdmin BIT,
)

CREATE TABLE [Product](
	productID VARCHAR(8) NOT NULL PRIMARY KEY,
	[name] NVARCHAR(50),
	[description] NVARCHAR(50),
	unitPrice FLOAT NOT NULL,
	quantity INT NOT NULL,
	[state] BIT NOT NULL
)

CREATE TABLE [OrderDetail](
	orderDetailID INT IDENTITY(1, 1) PRIMARY KEY,
	productID VARCHAR(8) NOT NULL,
	orderID VARCHAR(5) NOT NULL,
	unitPrice FLOAT NOT NULL,
	quantity INT NOT NULL,
	total FLOAT NOT NULL, 
)

CREATE TABLE [Order] (
	orderID VARCHAR(5) NOT NULL PRIMARY KEY,
	orderDate DATETIME NOT NULL,
	userAddress NVARCHAR(250) NOT NULL,
	total FLOAT
)

ALTER TABLE [OrderDetail]
	ADD CONSTRAINT FK_Order_OrderDetail FOREIGN KEY (orderID)
	REFERENCES [Order](orderID)

ALTER TABLE [OrderDetail] 
	ADD CONSTRAINT FK_Product_OrderDetail 
		FOREIGN KEY (productID)
		REFERENCES [Product](productID)

CREATE SEQUENCE ID_sequence
AS INT
START WITH 1
INCREMENT BY 1 
MAXVALUE 999
NO CYCLE

DROP TRIGGER FormatOrderTrigger

CREATE TRIGGER FormatOrderTrigger
ON [Order]
INSTEAD OF INSERT 
AS
BEGIN
	DECLARE @NewID VARCHAR(5)
	SET @NewID = 'HD' + FORMAT(NEXT VALUE FOR ID_sequence, '000')
	
	DECLARE @OrderDate DATETIME
	SET @OrderDate = GETDATE()

	INSERT INTO [Order] (orderID, orderDate, userAddress, total)
	SELECT @NewID, @OrderDate, userAddress, total
    FROM inserted
	
	SELECT @NewID AS orderID
END

SELECT * FROM [Order] 

SELECT * FROM [Order], [OrderDetail], [Product]
WHERE 
	[Order].orderID = [OrderDetail].orderID 
	AND
	[OrderDetail].productID = [Product].productID
