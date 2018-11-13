Итоговое домашнее задание по курсу "Автоматизация функционального тестирования с использованием Selenium"
=

Используя все изученные инструменты (**Java + Selenium + Cucumber + Allure + Maven**), создать проект автотестов для следующих
сценариев:

**Сценарий №1:**

*	Перейти на https://www.ozon.ru/
*	Развернуть окно на весь экран
*	Выбрать пункт меню – Электроника
*	Выбрать категорию – Телефоны
*	Выбрать производителя – Apple
*	Заполнить цена от – 50000
*	Добавить первый товар в корзину, запомнить название и цену
*	Перейти в корзину
*	Проверить, что в корзине есть добавленный товар
*	Нажать на удалить все
*	Проверить, что корзина пуста.

**Сценарий №2:**

*	Перейти на https://www.ozon.ru/
*	Развернуть окно на весь экран
*	Выбрать пункт меню – Электроника
*	Выбрать категорию – Зеркальные фотоаппараты
*	Выбрать производителя – Nikon, Canon
*	Заполнить цена от – 80000
*	Добавить первый товар в корзину, запомнить название и цену
*	Перейти в корзину
*	Проверить, что в корзине есть добавленный товар
*   Нажать на удалить все
*	Проверить, что корзина пуста.

---

### Особенности реализации:

*   В "Сценарии №1" выбор цены реализовал в стиле **"в диапазоне "ОТ" и "ДО"""** (От 50000 до 70000 рублей). 
Добавил дополнительный перегруженный метод для выбора фильтра цены.

```
public void inputPriceRange(String priceFrom, String priceTo) {
//...
}

public void inputPriceRange(String priceFrom) {
//...
}
```

*   В обоих сценариях при выборе найденных товаров, расширил выбор на **несколько первых товаров**.
При этом программа контроллирует попадание в выборку товаров с пометкой "НЕТ В НАЛИЧИИ". Она их пропускает.
А также тест падает с контроллируемой ошибкой в случае, если в нём задано больше товаров, чем выдал поиск.

*   Добавил дополнительный **Сценарий №3**, чтобы показать, как итоговый отчёт формируется со скриншотом бага.
Это негативный сценарий, завершающийся с ошибкой. Конфигурацию все проекта сделал так, что формирование отчёта 
и выполнение тестов не прекращается при возможных ошибках и багах.

![image](/doc/img/failed_attach.png)

*   Для большей наглядности отчёта добавил методы добавления предварительных **вложений**. Скриншоты и текстовые файлы.
Например, во время добавления товаров в Корзину записываем их наименование и цену:

![image](/doc/img/text_attach.png)

После проверки Корзины добавляем большой скриншот **её содержимого.**

![image](/doc/img/failed_attach.png)

После очистки Корзины ещё один скриншот **подтверждает, что она пуста.**

![image](/doc/img/cart_attach.png)

*   Предварительные скриншоты можно **отключить** изменением переменной _isScreenshotAllowed_ в классе **BasePage.class**.
Добавил флаг на всякий случай. Если вдруг захочется облегчить отчёт или убрать warnings метода снятия скринов.

*   Предварительные скриншоты снимаются с помощью дополнительной библитеки **AShot**. Сторонняя библиотека умеет снимать
скриншоты большой площади. Стандартные скриншоты из Selenium, к сожалению, получаются размером с марочку. 

---

**SO LONG AND THANKS FOR ALL THE FISH!**