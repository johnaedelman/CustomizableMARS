.data
str1: .asciiz "testing the print...\n"
str2: .asciiz "second string !!!\n"
.text

pow $t1, 8000
pow $t2, 2
kai $t1, $t2


PRINT:
tp str1
pow $t1, -100
it $t1, PRINT

pow $t3, 11
pow $t4, 11
sb $t7
ssj $t7
