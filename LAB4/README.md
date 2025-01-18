JMS сделали через ActiveMQ

Сообщения отправляются

![image](https://github.com/user-attachments/assets/1f9f6d85-b65f-4d99-935d-1a63162d4ab2)


Логирование ведется:


![image](https://github.com/user-attachments/assets/98ee4c60-0744-4acd-b692-26979584e9f3)


Вторая таблица для проверки логов для отправки на почту.

![image](https://github.com/user-attachments/assets/cf2a2805-18ad-440e-85ed-7735378465da)


Как пример :

![image](https://github.com/user-attachments/assets/5ef9e612-ca96-4581-9a90-e763af4de39c)

![image](https://github.com/user-attachments/assets/05ffafc1-fda9-4dcb-9a9e-64b48d2f10af)

![image](https://github.com/user-attachments/assets/2eefbac9-c025-42be-9bd6-29d14fd860ef)



Обновление (Переделал под топик и проверил):

Создал еще одного слушателя, который просто выводит сообщение в коносль:


![image](https://github.com/user-attachments/assets/b2e9ddd7-5901-4e6a-b80d-310df5d8deaf)

Работает как для включенного слушателя, так и для выключенного, когда он включится сообщение придет.
