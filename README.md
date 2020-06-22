# Sites Chacking Service
---
 A Java program that provides a service for checking sites for changes and sending a message to the mail with information about changes

# Installation
---
- Build the project by Gradle 
- [Download](https://drive.google.com/file/d/1jnianaKsf3K36ykX6KyuZDdJjVN4fH3Y/view?usp=sharing) Jar File (`sitecheckingservice -1.0-SNAPSHOT`)
# Usage
---
### Flags
- ##### Input file name with the state of the sites for yesterday
  * With `--yesterday_state` <file_name>
  * With `--y_state` <file_name> 
- ##### Input file name with the state of the sites for today
  * With `--today_state` <file_name>
  * With `--t_state` <file_name>
- ##### Email from which the message is sent
  * With `--from_send` <file_name>
- ##### Password email from which the message is sent
  * With `--from_send_password` 
- ##### Email to which the message is sent
  * With `--to_send` 
- ##### Shows information about program options
  * With `--help` 
  * With `-h` 
# Example
```
Hello, n.bairamov@g.nsu.ru,
Over the past 24 hours, the following changes have occurred in the sites entrusted to you:
The following pages have disappeared:
example_url_3.com
The following new pages have appeared:
example_url_4.com
The following pages have changed:
The following pages without changes:
example_url_1.com
example_url_2.com
```

# License
----
MIT

----
> GitHub: [@baymxs](https://github.com/Baymxs) 
VK: [@baymxs](https://vk.com/endecv)

