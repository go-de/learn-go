(ns tools.async-test)

#?(:clj
   (defmacro deftest-go [name & body]
     `(devcards.core/deftest ~name
        (cljs.test/async done#
                         (cljs.core.async/go
                           ~@body
                           (done#))))))
