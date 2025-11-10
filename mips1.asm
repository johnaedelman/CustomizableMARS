.data
message: .asciiz "Power level drained by 500!!!\n"
.text
pow $t1, 8001
pow $t2, 4
kai $t1, $t2
loop:
pow $t1, -500
tp message
it $t1, loop