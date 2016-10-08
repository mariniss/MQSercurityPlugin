MQSecurityPlugin
======

It is a standard Active MQ plugin that implements the security logic necessary to send and fetch the data from the queues used as communication channel in the EasyIoTConnect platform, main project [here](https://github.com/mariniss/EasyIoTConnect).

Each Raspberry Pi Client has three queues (one for each direction and an other one for the temperature data), the access to the queues is allowed only by the Raspberry client for which it was created.

The pugling interacts with [EIoTBoServer](https://github.com/mariniss/EIoTCBoServer) service to check the client access data
