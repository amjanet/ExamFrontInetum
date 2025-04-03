Feature: Carga de Archivo

  Scenario Outline: Cliente completa una compra exitosamente
    Given que soy un cliente de Sauce Demo
    When inicio sesión con el usuario "<usuario>" y la contraseña "<contraseña>"
    And agrego "<producto>" al carrito
    And procedo al checkout
    And ingreso mi información de envío "<nombre>"  "<apellido>" y "<codigo_postal>"
    And confirmo la compra
    Then la compra se completa exitosamente

    Examples:
      | usuario         | contraseña   | producto            | nombre   | apellido | codigo_postal |
      | standard_user   | secret_sauce | Sauce Labs Backpack | Janet    | Aranda   | 5030          |
      | locked_out_user | secret_sauce | Sauce Labs Backpack | Margaret | Moscoso  | 5030          |

