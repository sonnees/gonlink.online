package session

import (
    "github.com/gorilla/sessions"
)

var Store sessions.Store

func Setup(store sessions.Store) {
    Store = store
}
// http://127.0.0.1:5500/?authenticated=true&name=sonnees&email=sson12131415%40gmail.com&avatar=https%3A%2F%2Favatars