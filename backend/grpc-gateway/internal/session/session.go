package session

import (
    "github.com/gorilla/sessions"
)

var Store sessions.Store

func Setup(store sessions.Store) {
    Store = store
}