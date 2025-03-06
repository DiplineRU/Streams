ru text
# Условия домашки
> На связи домашнее задание урока 4.6 потоки.

Цель сегодняшней домашней работы — написать несколько синхронизированных и не синхронизированных потоков.

### Шаг 1

Создать в StudentController эндпоинт GET /students/print-parallel.

Эндпоинт должен выводить в консоль имена всех студентов в параллельном режиме, а именно:

  - первые два имени вывести в основном потоке
  - имена третьего и четвертого студента вывести в параллельном потоке
  - имена пятого и шестого студента вывести в еще одном параллельном потоке.
Для вывода используйте команду System.out.println().

В итоге в консоли должен появиться список из шести имен в порядке, возможно отличном от порядка в коллекции.

### Шаг 2

Создать в StudentController эндпоинт GET /students/print-synchronized.

Эндпоинт должен выводить в консоль имена всех студентов в синхронном режиме.

Для этого создайте отдельный синхронизированный метод для вывода имен в консоль.

Далее необходимо, используя ранее созданный синхронизированный метод :

- первые два имени вывести в основном потоке
- имена третьего и четвертого студента в параллельном потоке
- имена пятого и шестого студента в еще одном параллельном потоке

В итоге в консоли должен появиться список из шести имен в порядке, возможно отличном от порядка в коллекции.

eng text
# Homework conditions
> The homework of lesson 4.6 streams is in touch.

The purpose of today's homework is to write multiple synchronized and out—of-sync threads.

### Step 1

Create a GET /students/print-parallel endpoint in the StudentController.

The endpoint should output the names of all students to the console in parallel mode, namely:

  - output the first two names in the main stream
  - output the names of the third and fourth students in a parallel stream
  - print the names of the fifth and sixth students in another parallel stream.
For output, use the System.out.println() command.

As a result, the console should display a list of six names in an order possibly different from the order in the collection.

### Step 2

Create a GET /students/print-synchronized endpoint in the StudentController.

The endpoint should output the names of all students to the console in synchronous mode.

To do this, create a separate synchronized method for outputting names to the console.

Next, you need to use the previously created synchronized method :

- output the first two names in the main stream
- the names of the third and fourth students in the parallel stream
- the names of the fifth and sixth students in another parallel stream

As a result, the console should display a list of six names in an order possibly different from the order in the collection.
