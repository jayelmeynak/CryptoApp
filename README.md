# Crypto

Приложение, разработанное в рамках курса "Android профессиональный уровень", показывает текущую цену различных криптовалют в долларах с возможностью просмотра более детальной информации про конкретную криптовалюту

## **Функциональные требования**

### Экран списка криптовалют

На данном экране отображается список критовалют с ценой, а также с последним временем обновления. Список обновляется автоматически с помощью WorkManager, который отправляет запрос на api через retrofit2. Полученный результат сохраняется в базе данных(Room) и далее из нее отображается на экране.

### Экран детальной информации

На экране отображаются текущая, минимальная, максимальная за день цена криптовалюты, иконка криптовалюты и дата обновления информации.

## Стек
 - Язык: kotlin
 - Работа с базой данных: Room
 - Работа с сетью: Retrofit2
 - Работа с фоновым режимом: WorkManager
 - Многопоточность: Kotlin Coroutine
 - View: XML
 - Архитектура: Clean Architecture
 - Паттерн проектирования: MVVM

## **Инструкция по запуску**

1. Клонируйте репозиторий:
   ```bash
   git clone <URL-репозитория>
2. Откройте проект в Android Studio.
3. Соберите и запустите приложение на эмуляторе или устройстве Android.
