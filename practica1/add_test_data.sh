#!/usr/bin/expect

spawn rm BRANCH.DAT
spawn ./hrm_backup

expect "ENTER YOUR CHOICE"
send "1\n"
expect "ENTER U R CHOICE"
for {set i 0} {$i < 5} {incr i 1} {
    send "03\n"
    expect "ENTER BRANCH CODE"
    send "$i\n"
    expect "ENTER BRANCH NAME"
    send "branch$i\n"
    expect "ENTER BRANCH ADDRESS"
    send "address$i, city$i, country$i\n"
    expect "ENTER PHONE"
    send "phone$i\n"
    expect "ENTER E-MAIL"
    send "email$i\n"
    expect "ENTER MANAGER NAME"
    send "manager$i\n"
    expect "ENTER U R CHOICE"
}
send "12\n"
expect "ENTER YOUR CHOICE"
send "3\n"
