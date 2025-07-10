resource "aws_instance" "app_server" {
  ami             = "ami-036f48ec20249562a" # UBUNTU 22.04
  instance_type   = "t2.medium"
  key_name        = aws_key_pair.my_key.key_name
  security_groups = [aws_security_group.instance_sg.name]
  tags = {
    Name = "SGDTR-Instance"
  }

  root_block_device {
    volume_size           = 20
    volume_type           = "gp3"
    delete_on_termination = true # Exclui o volume ao terminar a instância
  }
}

