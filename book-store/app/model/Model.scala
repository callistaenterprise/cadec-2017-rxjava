package model

case class Order(book: Book, address: Address, coordinates: Coordinates) {
	def this(book: Book, address: Address) = this(book, address, null)

	var orderId: Integer = _

	def setCoordinates(coordinates: Coordinates) = {
		val order = Order(book, address, coordinates)
		order.orderId = orderId
		order
	}

	def setOrderId(orderId: Integer) = {
		val order = Order(book, address, coordinates)
		order.orderId = orderId
		order
	}

	def getBook = book
	def getAddress = address
	def getCoordinates = coordinates
	def getOrderId = orderId

}
