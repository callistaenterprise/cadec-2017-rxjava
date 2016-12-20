package model

case class Order(book: Book, address: Address) {
	var coordinates: Coordinates = _
	var orderId: Integer = _

	def setCoordinates(coordinates: Coordinates) = {
		val order = Order(book, address)
		order.coordinates = coordinates
		order.orderId = orderId
		order
	}

	def setOrderId(orderId: Integer) = {
		val order = Order(book, address)
		order.coordinates = coordinates
		order.orderId = orderId
		order
	}

	def getBook = book
	def getAddress = address
	def getCoordinates = coordinates
	def getOrderId = orderId

}
