package services

import model._

class DroneOrderServiceScala(booksService: BooksService, personnummerService: PersonnummerService,
                             coordinateService: CoordinateService, bookOrderService: BookOrderService) {

//	def placeDroneOrder(orderForm: OrderForm) = {
//
//		val bookSingle = booksService getBook(orderForm.getBookTitle, orderForm.getBookAuthor)
//		val addressSingle = personnummerService getAddressByPersonnummer orderForm.getPnr
//
//		for {
//			book <- bookSingle
//			address <- addressSingle
//			coordinates <- coordinateService.getCoordinate(address)
//			order <- bookOrderService.sendOrder(Order(book, address, coordinates))
//		} yield order
//	}

}
