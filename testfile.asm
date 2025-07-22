li $t0, 3
LOOP:

move $t1, $v0

li $v0, 1
move $a0, $t1
syscall
li $a0, 10
li $v0, 11
syscall

move $v0, $t1
addi $v0, $v0, 1
bne $v0, 5, LOOP