package be.technifutur.devmob9.sdsftryone.webservice

class Event (var id: Int = 0,
             var match: Int = 0,
             var day: Int = 0,
             var champ: Int = 0,
             var time: String = "", // minute affichée + seconde
             var team: String = "",
             var type: String = "",
             var param: String = "",
             override var action: Char = 'A') : ModificationData