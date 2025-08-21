.data
str1: .asciiz "testing the print...\n"
str2: .asciiz "second string !!!\n"
.text

addi $t5, $zero, 5
LOOP:
addi $t0, $t0, 1
print $t1, str1
print $t2, str2
bne $t0, $t5, LOOP

