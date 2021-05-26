# Word-Letter-Game
- UDP uses a simple connectionless communication model with a minimum of protocol mechanism. Reliability mechanisms can be added to the application layer if needed. The following are things you need to do to ensure reliability in “word-letter game” for UDP:
1) There is no handshaking between UDP sender and receiver before packets start being exchanged. The handshaking must be established using the acknowledgement message. For example, first packet may contain request message for playing game.
2) When a UDP packet is sent, it cannot be known if it will reach its destination; it could get lost along the way. When the packet is received by the receiver, it must send the acknowledgement message to the sender.

