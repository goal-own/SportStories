from VkApiManager import VkApiManager

vk_api_manager = VkApiManager(user=input("USER:"), password=input("PASSWORD:"))

friends = vk_api_manager.get_friends(fields=["nickname"])

for friend in friends:
    print(str(friend))
