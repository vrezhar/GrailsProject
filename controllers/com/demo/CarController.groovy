package com.demo

class CarController {

    def list(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        [carInstanceList: Car.listOrderByModel(params),
         carInstanceTotal: Car.count()]
    }
}
