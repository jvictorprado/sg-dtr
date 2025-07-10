resource "aws_key_pair" "my_key" {
  key_name   = "sgdtr-key-pair"
  public_key = file("~/.ssh/id_rsa.pub")
}

